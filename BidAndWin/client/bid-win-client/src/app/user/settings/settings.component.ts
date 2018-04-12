import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../../auth.service';
import { Observable } from 'rxjs/observable';
import { Router } from '@angular/router';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  model:{
    password: string,
    passwordAgain: string,
  }
  model2:{
    category:string,
  }
  @ViewChild('form') form;
  constructor(
    private authService: AuthService,
    private router: Router,
  ) {
    this.model={
      "password":'',
      "passwordAgain":'',
    };
    this.model2={
      "category":'',
    }
   }
  ngOnInit() {
  }

  pwsNotMatching(): boolean{
    return(this.model.password!=this.model.passwordAgain);
   }
  onSubmitCategory(){
    /*if(this.form.valid){}
    kell még egy itemService több formview (viewchildren maybe)...
    */
  }
  onSubmitPW(){
    /* ehhez kellene egy szerver oldali végpont! */
     if(this.form.valid){
      if(this.pwsNotMatching()){}
      else{
        this.authService.changePassword(this.model.password)
        .then(() =>{
          this.router.navigateByUrl('');
          alert("Sikeres jelszó módosítás!")
        });
      }
    }
  }
}
