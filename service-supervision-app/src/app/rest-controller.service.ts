import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StockLevel } from './model';

@Injectable({
  providedIn: 'root'
})
export class RestControllerService {

  constructor(
    private httpClient: HttpClient
  ) { }

  
  initAllServices(){
    this.initService(8091,1000).subscribe();
    this.initService(8092,200).subscribe();
    this.initService(8093,80).subscribe();
    this.initService(8094,60).subscribe();
  }

  initService(portNumber: number, stockLevel: number): Observable<StockLevel> {
    return (this.httpClient.post(
      "http://localhost:"+portNumber+"/api/stock?stockLevel="+stockLevel,{}
    ) as Observable<StockLevel>);
  }

}
