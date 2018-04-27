import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../../auth.service';
import { ItemService } from '../../item.service';
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
    id : number,
    name : string,
  }
  @ViewChild('form') form;
  constructor(
    private authService: AuthService,
    private itemService: ItemService,
    private router: Router,
  ) {
    this.model={
      "password":'',
      "passwordAgain":'',
    };
    this.model2={
      id: -1,
      "name":'',
    }
   }
  ngOnInit() {
  }

  pwsNotMatching(): boolean{
    return(this.model.password!=this.model.passwordAgain);
   }
   categoryExists(): boolean{
     console.log(this.itemService.getCategoriesName());
    return this.itemService.getCategoriesName().indexOf(this.model2.name)>-1;   
   }
  onSubmitCategory(){
    if(this.form.valid){
      this.model2.name=this.model2.name.trim();
      if(!this.categoryExists()){
        this.itemService.addCategory(this.model2);
      }else{
      }
    }
  }
  /*onSubmitPW(){
    // ehhez kellene egy szerver oldali végpont! 
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
  }*/
}
