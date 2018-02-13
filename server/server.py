#!/usr/bin/env python
from __future__ import print_function
import socket
import sys
import sqlite3
import json
import time


HOST = '132.75.55.157'
PORT = 7373

class Payload(object):
     def __init__(self, j):
         self.__dict__ = json.loads(j)
        

time.sleep(10)
log = open("/home/pi/serverOutput.txt", "w", 0)
connection = sqlite3.connect('/home/pi/mydatabase.db')
curs=connection.cursor()
print ("\nconnecting to DB\n", file=log)
        
s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
print ("socket created", file=log)
while True:
     try:
          s.bind((HOST,PORT))
     except socket.error as err:
          print ("bind failed, Error Code: " + str(err[0])+ ",Msg: " + err[1], file=log)
          time.sleep(10)
          continue
     print ("socket bind success", file=log)
     break

s.listen(10)
print ("socket listening", file=log)
while True:
     conn, addr = s.accept()
     print ("connection by: " + addr[0] + ":" + str(addr[1]) , file=log)
     if addr[0] == '132.75.55.194':
        execfile("/home/pi/OpenDoor.py")
        print ("Opening door for Edison", file=log)
        conn.send("Success\r\n")
        conn.close()
        continue
     data = conn.recv(64)
          
     req = Payload(data)
     name = req.username
     pas = req.password
     isLogin = req.isLogin
     print (data, file=log)
          
     isPresent = curs.execute('SELECT * FROM users WHERE username=? AND password=?',(name,pas)).fetchall()
     if isPresent == []: 
          result = "Invalid"
     else:
          result = "Success"
          if isLogin == False:
               execfile("/home/pi/OpenDoor.py")
               print ("blinking", file=log)
     print (result+"\n", file=log)
     conn.send(result + '\r\n')
     conn.close()
          
s.close()
