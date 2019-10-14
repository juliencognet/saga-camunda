import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StockLevel } from './model';

@Injectable({
  providedIn: 'root'
})
export class OrchestratorControllerService {

  constructor(
    private httpClient: HttpClient
  ) { }

  runTransaction(transactionAmount: number, processKey: string, processVariable: string): Observable<any> {
    let parameterBody =JSON.parse(
      ' { "variables": { "'+processVariable+'" : { "value": '+transactionAmount+', "type": "long" } } }');
    return (this.httpClient.post(
      "http://localhost:8080/rest/process-definition/key/"+processKey+"/start",parameterBody
    ) as Observable<any>);
  }

}
