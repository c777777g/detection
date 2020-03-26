# detection

 这是个检查环境设备的后台管理器
 后台展示设备端捕捉的环境数据（温度，湿度，光度，pm2.5等等），后台里面有用户操作日志，设备数据报表，弹窗报警等等

后台使用mina与设备进行长连接控制，
前端浏览器通过websocket与后台实时更是设备的数据
![Image text](https://raw.githubusercontent.com/c777777g/detection/master/document/1.png)
![Image text](https://raw.githubusercontent.com/c777777g/detection/master/document/2.png)
#### 上线下线告知
![Image text](https://raw.githubusercontent.com/c777777g/detection/master/document/3.png)
![Image text](https://raw.githubusercontent.com/c777777g/detection/master/document/4.png)
#### 环境数据异常报警
![Image text](https://raw.githubusercontent.com/c777777g/detection/master/document/5.png)
####  数据哲线图等等





## Json格式通信协议表格

设备使用json协议与后台长连接通信

端口：7777

**连接服务器**

clien → server   {"opcode":"login","deviceId":"0001" ,"ip":"192.168.1.110"}/n	登陆信息

server→ clien	{"opcode":"loginAnswer"," status ":" 1"," message ":" success "," datetime":1540435482000 }/n	服务返回状态


**心跳**

clien → server	{"opcode":"heartbeat"}/n	

server→ clien	{"opcode":"heartbeat"}	


**检测数据上报**

 clien → server	{"opcode":"reportedData","deviceId":"0001","temperature":20,"pm25":50,"humidity":50,"opticalInductor":50,"illuminatingBrightness":0,"latlng":"[23.1666981,113.429923]","bodyInductor":0,"smokeSensors":0}/n



**协议含有相关字段则开启该功能**

server→ clien   {"opcode":"setIP","deviceId":"xxx"," IPAddress ":"192.168.1.68"," subnetMask":"255.255.255.0"," gateway":"192.168.1.1" }/n

		
**UDP广播修改服务器地址**

UDP订阅端口是：7776

 server → 局域网	{"opcode":"spotServer","ip":"192.168.1.50","port":"7777"}

