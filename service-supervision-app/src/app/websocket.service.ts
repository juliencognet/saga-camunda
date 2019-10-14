import { Injectable } from '@angular/core';
import { StateAndTransactions } from './model';
import { Subject } from 'rxjs';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  constructor(
    //private toastr: ToastrService
  ) { }

  subjectMap = new Map<string, Subject<StateAndTransactions>>();
  stompClientMap = new Map<string, Stomp>();

  connectAndSubscribeToWebSocketEvents(service: string, port: number) : Subject<StateAndTransactions>{
    // Create subject if necessaray
    if (!this.subjectMap.get(service)) {
      this.subjectMap.set(service, new Subject<StateAndTransactions>());
    }
    console.log('Trying to connect to websocket on ' + service);
    const that = this;
    let serviceUrl: string = 'http://localhost:'+port+'/ws';
    const ws = new SockJS(serviceUrl);
    let stompClient = Stomp.over(ws);
    this.stompClientMap.set(service,stompClient);
    //that.toastr.info('Tentative de connexion à '+serviceUrl);

    // Switch off debug
    stompClient.debug = () => { };

    // Error handlers. There are many other methods also
    stompClient.onDisconnect = () => { that.reConnectWebSocket(service,port); };
    stompClient.onStompError = () => { that.reConnectWebSocket(service,port); };
    // This is the most important
    stompClient.onWebSocketClose = () => { that.reConnectWebSocket(service,port); };

    stompClient.connect({}, function () {
          console.log('Websocket connection established...');
          //that.toastr.success('Connexion établie', 'Websocket ouverte avec '+ service );

          // Subscribe to WS change 
          that.stompClientMap.get(service).subscribe('/topic/stateAndTransactions', (message) => {
              console.log(message);
              if (message.body) {
                const messageUpdate: StateAndTransactions = JSON.parse(message.body);
                console.log("Received update "+messageUpdate+" for service "+service);
                that.subjectMap.get(service).next(messageUpdate);
              }
            }
          );
          // Provoque le premier évenement
          setTimeout(()=>{
            console.log("About to subscribe");
            that.stompClientMap.get(service).send('/app/topic/subscribe')
          },100);

      }, function (error) {
          //that.toastr.error('Impossible de se connecter', error);
          that.reConnectWebSocket(service,port);
      });
      return this.subjectMap.get(service);
  }

  reConnectWebSocket(serviceUrl : string,port: number) {
    const retryTimeout = 2;
    const that = this;
    // Call the websocket connect method
    setTimeout(function() { that.connectAndSubscribeToWebSocketEvents(serviceUrl,port); }, retryTimeout * 1000);
  }

}
