import { Component, OnInit } from '@angular/core';
import { Item } from '../../../item';
import { ItemService } from '../../../item.service';
import { AuthService } from '../../../auth.service';
import { Router } from '@angular/router';
declare global {
  interface Window {
    IMAGE_RESULT?: string;
  }
}
@Component({
  selector: 'app-itemupload',
  templateUrl: './itemupload.component.html',
  styleUrls: ['./itemupload.component.css']
})

export class ItemuploadComponent implements OnInit {
  model: Item;
  constructor(
    private itemService: ItemService,
    private authService: AuthService,
    private router: Router,
  ) {
    this.model={}
  }
  isLoggedIn(){
    if(this.authService.isLoggedIn()){
      return this.authService.isLoggedIn();
    }else{
      //this.router.navigateByUrl('/login');
      return false;
    }
  }
  readUrl(event:any) {
    if (event.target.files && event.target.files[0]) {
      var reader = new FileReader();
  
      reader.onload = (event:any) => {
        let path = event.target.result;
      }
      let base;
      reader.readAsDataURL(event.target.files[0]);
      reader.onload = e => {
        base=reader.result;
        window.IMAGE_RESULT = base;
       };
    }
  }
  
  onSubmit(){
    this.model.category={id:this.model.categoryId};
    this.model.categoryId=undefined;
    this.model.picture=window.IMAGE_RESULT.substring(23);
    this.itemService.uploadItem(this.model);
    //this.router.navigateByUrl("/index");
  }
  ngOnInit() {
  }

}
