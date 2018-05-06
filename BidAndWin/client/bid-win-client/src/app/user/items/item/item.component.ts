import { Component, OnInit } from '@angular/core';
import { Item } from '../../../item';
import { ItemService } from '../../../item.service';
import { AuthService } from '../../../auth.service';
import { Bid } from '../../../bid';
import { Router } from '@angular/router';
declare global {
  interface Window {
    IMAGE_RESULT?: string;
  }
}
@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent implements OnInit {

  item: Item;
  model:{bid?:number};
  constructor(
    private itemService : ItemService,
    private authService : AuthService,
    private router : Router,
  ) {
    this.item = this.itemService.getChosenItem();
    this.model={bid:this.item.currentPrice+this.item.bidIncrement};
  }
  isOwner():boolean{
    return this.authService.getLoggedInUsername()===this.item.user.username;
  }
  vege():boolean{
    return this.authService.time.valueOf()>this.item.endTime.valueOf();
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
  makebid(){
    let bid:Bid={item:{id:this.item.id}, bidOffer:this.model.bid};
    this.itemService.makebid(bid);
    this.router.navigateByUrl('/index');
  }
  ngOnInit() {
  }
  modify(){
    this.item.picture=window.IMAGE_RESULT.substring(23);
    window.IMAGE_RESULT=undefined;
    this.itemService.updateItem(this.item);
  }

}
