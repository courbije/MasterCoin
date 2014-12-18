/*
	By Osvaldas Valutis, www.osvaldas.info
	Available for use under the MIT License
*/



function arrondir(table_id){
	
	if (!document.getElementById(table_id) || document.getElementById(table_id).rows[0].cells.length!=6)
		return;
	 var arrayLignes = document.getElementById(table_id).rows; 
	 var longueur = arrayLignes.length;
	 for(var i=1; i < longueur; i++){
		 if (arrayLignes[i].cells[5].innerHTML.indexOf("<")>-1){
			 if (arrayLignes[i].cells[5].innerHTML.indexOf('<span style="color : black">')>-1){
				 s = arrayLignes[i].cells[5].innerHTML.replace('<span style="color : black">',"");
				 s = s.replace('</span>',"");
				 s = Math.round(s*1000000)/1000000;
				 s = "<span style='color : black'>"+ s +"</span>";
				 arrayLignes[i].cells[5].innerHTML=s;
			 }
			 else {
				 s = arrayLignes[i].cells[5].innerHTML.replace('<span style="color : blue">',"");
				 s = s.replace('</span>',"");
				 s = Math.round(s*1000000)/1000000;
				 s = '<span style="color : blue">'+ s +'</span>';
				 arrayLignes[i].cells[5].innerHTML=s;
			 }
		 }
		 else {
			 arrayLignes[i].cells[5].innerHTML=Math.round(arrayLignes[i].cells[5].innerHTML*1000000)/1000000;
		 }
	 } 
		
}
