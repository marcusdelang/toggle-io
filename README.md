# toggle-io
Code for input and output against "toggle-api"


LINUX USER replace eth0 with your internet interface found from ifconfig
As superuser run:
iptables -t nat -A PREROUTING -i eth0 -p tcp --dport http -j REDIRECT --to-ports 8080


