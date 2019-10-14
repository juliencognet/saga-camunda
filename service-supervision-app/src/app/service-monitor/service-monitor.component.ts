import { Component, OnInit, Input} from '@angular/core';
import { StateAndTransactions } from '../model';
import { WebsocketService } from '../websocket.service';

@Component({
  selector: 'app-service-monitor',
  templateUrl: './service-monitor.component.html',
  styleUrls: ['./service-monitor.component.css']
})
export class ServiceMonitorComponent implements OnInit {

  @Input() typeOfService: string;
  @Input() port: number;

  public stateAndTransactions: StateAndTransactions;

  constructor(
    private webSocketService : WebsocketService
  ) { }

  ngOnInit() {
    //Subscribe to websocket
    this.webSocketService.connectAndSubscribeToWebSocketEvents(this.typeOfService,this.port).subscribe(
      (newState : StateAndTransactions) => {
        console.log("Received update in component");
        this.stateAndTransactions = newState;
        console.log("Now stock = "+this.stateAndTransactions.stockLevel.currentStockLevel);
      }
    )
  }

}
