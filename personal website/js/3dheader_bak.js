//首部的3d特效的内容
var varText = ""
function addInfo(title, photourl, sphotourl) {
	if(varText != "") {
		varText += "|||";
	}
	varText += title + "|_|" + photourl + "|_|" + sphotourl;
}

addInfo("", "images/3dheader/3d1.jpg", "images/3dheader/s3d1.jpg");
addInfo("", "images/3dheader/3d2.jpg", "images/3dheader/s3d2.jpg");
addInfo("", "images/3dheader/3d3.jpg", "images/3dheader/s3d3.jpg");
document.write('<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" width="972" height="310" id="01" align="left">')
document.write('<param name="allowScriptAccess" value="sameDomain" />')
//document.write('<param name="class" value="3dobject" />') //设置class没有起到作用
document.write('<param name="movie" value="images/3dheader/main.swf?info=' + varText + '" />')
document.write('<param name="quality" value="high" />')
document.write('<param name="bgcolor" value="#B6B6B6" /><param name="wmode" value="opaque" />')
//wmode属性很重要，opaque 影片隐藏了所有在它后面的内容。transparent 使flash影片透明，显示透明影片后面的网页内容。
document.write('<embed src="images/3dheader/main.swf?info=' + varText + '"" quality="high" bgcolor="#ffffff" width="972" height="310" name="01" align="left" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />')
document.write('</object>')
