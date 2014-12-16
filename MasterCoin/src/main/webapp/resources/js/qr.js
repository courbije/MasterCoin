function read(a)
{
    $("#qr-value").text(a);
	alert(a);
	// Envoyer cette valeur dans un formulaire et le submit
}
    
qrcode.callback = read;