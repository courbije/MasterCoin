var final_transcript = '';
var recognizing = false;
var ignore_onend;
var start_timestamp;

// Speech Recognition
if (!('webkitSpeechRecognition' in window)) {
    message.innerHTML = 'L\'API Web Speech n\'est pas supportée par ce navigateur. Utilisez <a href="//www.google.com/chrome">Chrome</a> version 25 ou plus.';
} else {
    var recognition = new webkitSpeechRecognition();
    recognition.continuous = false;
    recognition.interimResults = false;
    
    recognition.onstart = function () {
        recognizing = true;
        message.innerHTML = 'Parlez maintenant.';
    };

    recognition.onresult = function (event) {
        var interim_transcript = '';
        for (var i = event.resultIndex; i < event.results.length; ++i) {
            if (event.results[i].isFinal) {
                final_transcript += event.results[i][0].transcript;
            } else {
                interim_transcript += event.results[i][0].transcript;
            }
        }
    };

    recognition.onend = function () {
        recognizing = false;
        if (ignore_onend) {
            return;
        }

        if (!ignore_onend) {
        	putAnswer(final_transcript);
            message.innerHTML = 'Cliquez sur "Parler" et donnez une commande.';
            return;
        }
    };

    recognition.onerror = function (event) {
        if (event.error == 'no-speech') {
            message.innerHTML = 'Aucun discours détecté.';
            ignore_onend = true;
        }
        if (event.error == 'audio-capture') {
            message.innerHTML = 'Aucun microphone n\'a été détecté. Assurez-vous que le microphone est bien installé.';
            ignore_onend = true;
        }
        if (event.error == 'not-allowed') {
            if (event.timeStamp - start_timestamp < 100) {
                message.innerHTML = 'La permission, pour utiliser le micro, a été bloquée. Pour changer les options, allez à l\'adresse suivante : chrome://settings/contentExceptions#media-stream';
            } else {
                message.innerHTML = 'La permission, pour utiliser le micro, a été refusée.';
            }
            ignore_onend = true;
        }
    };

}

function talkWithApp(event) {
    if (recognizing) {
        recognition.stop();
        message.innerHTML = 'Cliquez sur "Parler" et donnez une commande.';
        return;
    }
    final_transcript = '';
    recognition.lang = "fr-FR";
    recognition.start();
    message.innerHTML = 'Cliquez sur "Autoriser" au-dessus pour permettre d\'utiliser le micro';
    ignore_onend = false;
    start_timestamp = event.timeStamp;
}

// Placer la réponse saisie à l'orale dans le formulaire
function putAnswer(transcript) {
	console.log(transcript);
	transcript = transcript.toLowerCase();
	console.log(transcript);
	split = transcript.split(" ");
	
	var val=-1;
	
	if(split.length>1){
		//Il y a le mot "numéro"
		if(split.indexOf("numéro")!=-1){
			val=split[split.indexOf("numéro")+1];
		}else{
			val=split.pop();
			split.push(val);
		}
	}
	console.log(val);
	if(val!=-1 && !isNaN(new Number(val))){
		if(split.indexOf("quantité")==-1) {
			// Si on parle d'une monnaie
			if(split.indexOf("monnaie")!=-1) {
				if(split.indexOf("achat")!=-1 || split.indexOf("acheter")!=-1 || split.indexOf("souhaitée")!=-1) {
					// Monnaie voulue
					$("#reg\\:reg_monnaie").val(val);
				}else if(split.indexOf("vente")!=-1 || split.indexOf("vendre")!=-1){
					// Monnaie donnée
					$("#reg\\:reg_monnaie2").val(val);
				}
			}
		}else{
			//Sinon on parle d'une quantité
			if(split.indexOf("achat")!=-1 || split.indexOf("acheter")!=-1 || split.indexOf("souhaitée")!=-1 || split.indexOf("voulue")!=-1 || split.indexOf("achetée")!=-1){
				// Quantité voulue
				$("#reg\\:quantite_voulue").val(val);
			}else if(split.indexOf("vente")!=-1 || split.indexOf("vendre")!=-1 || split.indexOf("vendue")!=-1 || split.indexOf("donné")!=-1 || split.indexOf("donner")!=-1){
				// Quantité donnée
				$("#reg\\:quantite_vendue").val(val);	
			}
		}
	}else if(split.indexOf("enregistrer")!=-1 || split.indexOf("soumettre")!=-1){
		$("#reg\\:register").click();
	}
	retour.innerHTML="Commande saisie oralement : "+transcript;
}