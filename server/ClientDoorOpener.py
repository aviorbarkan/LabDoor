from __future__ import print_function
import socket
import sys

HOST = '132.75.55.157'
PORT = 7373

log = open("/home/motion/scripts/serverConnectionOutput.txt", "w", 0)

try:
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_address = (HOST, PORT)
    sock.connect(server_address)
    data = sock.recv(8)
    print ("Operation " + data, file=log) 

except socket.error as err:
    print ("Error: " + str(err[0])+ ",Msg: " + err[1], file=log)
    sys.exit()

sock.close()
