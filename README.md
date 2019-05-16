# toggle-io
Code for input and output against "toggle-api"

### __Hardware Requirement__
- Linux based operating system with telldus-core installed

- Java JDK 12 installed with ```java``` command available

- Internet connection with either globaly routed ip or a set port on router

- Tellstick Duo

- Atleast one of the following supported outlets:

  - Proove
    - Selflearning-switch
    - selflearning-dimmer
  - Nexa
    - Codeswitch


## __!Important!__

To run the application you will need to rerout the ip trafic from port 80 to port 8080 to avoid having to run the application as superuser



As superuser run the following command in your terminal and replace eth0 with your internet interface found from ifconfig:
```
iptables -t nat -A PREROUTING -i eth0 -p tcp --dport http -j REDIRECT --to-ports 8080
```

### __How to run__

