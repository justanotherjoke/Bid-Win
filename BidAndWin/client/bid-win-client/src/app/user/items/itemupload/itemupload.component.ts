import { Component, OnInit } from '@angular/core';
import { Item } from '../../../item';
import { ItemService } from '../../../item.service';
import { AuthService } from '../../../auth.service';
import { Router } from '@angular/router';

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
      this.router.navigateByUrl('/login');
      return false;
    }
  }
  onSubmit(){
    this.itemService.uploadItem(this.model);
  }
  ngOnInit() {
  }

}
