import { Component, OnInit, ViewChild } from '@angular/core';
import {ClrWizard} from "@clr/angular";
import { OrchestratorControllerService} from '../orchestrator-controller.service';

@Component({
  selector: 'app-launch-process',
  templateUrl: './launch-process.component.html',
  styleUrls: ['./launch-process.component.css']
})
export class LaunchProcessComponent implements OnInit {

  constructor(
    private orchestratorControllerService : OrchestratorControllerService
  ) { }

  ngOnInit() {
  }

  processKey: string;
  amount: number;
  processVariable: string;

  @ViewChild("wizardLaunchProcess") wizardLaunchProcess: ClrWizard;

  mdOpen : boolean = false;

  launchProcess(){
    this.wizardLaunchProcess.reset();
    this.mdOpen=true;
  }
  doLaunchProcess(){
    this.orchestratorControllerService.runTransaction(this.amount, this.processKey, this.processVariable).subscribe(
      (result: any) => {
        console.log("Launched process "+result.id);
      }, error => {
        console.log(error);
      }
    )
  }


}
