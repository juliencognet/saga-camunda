import { Component } from '@angular/core';
import { OrchestratorControllerService } from './orchestrator-controller.service';
import { RestControllerService } from './rest-controller.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'service-supervision-app';

  constructor(
    private orchestratorService: OrchestratorControllerService,
    private restService: RestControllerService
  ){}

  initServices(){
     console.log("About to init all services"); 
     this.restService.initAllServices();
  }

}
