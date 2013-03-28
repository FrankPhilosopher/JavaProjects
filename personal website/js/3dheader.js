//首部的3d特效的内容
var varText = ""
function addInfo(title, photourl, sphotourl) {
	if(varText != "") {
		varText += "|||";
	}
	varText += title + "|_|" + photourl + "|_|" + sphotourl;
}

addInfo("", "images/3dheader/1.jpg", "images/3dheader/spic1.jpg");
addInfo("", "images/3dheader/2.jpg", "images/3dheader/spic2.jpg");
addInfo("", "images/3dheader/3.jpg", "images/3dheader/spic3.jpg");
document.write('<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" width="932" height="316" id="01" align="middle">')
document.write('<param name="allowScriptAccess" value="sameDomain" />')
document.write('<param name="movie" value="images/3dheader/main.swf?info=' + varText + '" />')
document.write('<param name="quality" value="high" />')
document.write('<param name="bgcolor" value="#ffffff" /><param name="wmode" value="transparent" />')
document.write('<embed src="images/3dheader/main.swf?info=' + varText + '"" quality="high" bgcolor="#ffffff" width="932" height="316" name="01" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />')
document.write('</object>')
