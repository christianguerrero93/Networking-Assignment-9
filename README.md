# Networking-Assignment-9

Homework 9 Instructions



For this assignment you will be extending your work from Homework 6. This time we will all use the same port number, 64,000.

You will add a protocol to lookup the IP address of a particular user.

The protocol should work as follows:

    When initiating a messaging session, you will specify the name of the person you are trying to reach. This should result in a message broadcast to everyone on the local network asking for the IP address of the person. This message will be a String formatted as follows: "????? name-of-other-person ##### your-name". Please note that the name of the person should be one word with no spaces.
    Everyone will receive this message, but only the person wih that name will reply with a String formatted as follows: "##### name-of-other-person ##### ww.xx.yy.zz", where ww.xx.yy.zz is the dotted decimal IP address of the person you are trying to message with.
    The title of the messaging window should be the name of the person you are messaging plus their IP address.

Homework 6 Instructions



For this assignment you will be extending what you learned about socket programming in Datagram Socket Demo

You will be implementing a text messaging application. You will have a seperate window for each person you are communicating with. The window will have as a title the IP address and port number of the person you are messaging with.

You will have a main window that allows you to initiate messaging sessions with other people based on their IP address and port number. Once you initiate a new messaging session, you will create a new window for that session. This window should also have a button to exit the program.

You will still have the receiving Thread which will handle all the incoming messages. However, your code will also keep track of the source IP addresses and port number along with the window containing that messaging session. If the source IP address and port number combination is a new one, you will have to open a new messaging window. Otherwise, the incoming message will be displayed in the existing window for that source IP address.

Each window will have the following components:

    The title of the window should be the IP address and port number of the other side of the messaging session.
    A section to display the messaging interaction. This window should contain the messages from both you and the person you are messaging with.
    A section for you to type your reply.
    A button to send your reply.
    A button the close and end the messaging session.

