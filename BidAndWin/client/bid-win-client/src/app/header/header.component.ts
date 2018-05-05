import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { ViewChild } from '@angular/core/src/metadata/di';
//import { Router } from '@angular/router/src/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(
    private authService : AuthService,
    //private router: Router,
  ) {
    this.authService.getServerTime();
    setInterval(function(){
      let milisecs= new Date().valueOf() + authService.timeSub;
      authService.time=new Date(milisecs);
    },1000);
  }

  ngOnInit() {
  }

  public isLoggedIn(): boolean{
    return this.authService.isLoggedIn();
  }

}
