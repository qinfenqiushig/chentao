var http = require("http");
var util = require("util");
var urlObj = require("url");

var server = new http.Server();

server.on("request",function(req,res){
	var url =req.url;
	var method =req.method;
	var headers = req.headers;
	
	res.writeHead(200,{"Content-Type":"text/plain"});
	res.write("url string : "+url+"\n");
	res.write("url object : "+util.inspect(urlObj.parse(url,true))+"\n");
	res.write("method object : "+method+"\n");
	res.write("headers object : "+util.inspect(headers)+"\n");
	res.end();
});
server.listen(5858);