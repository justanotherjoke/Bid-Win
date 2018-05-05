import { Component, OnInit } from '@angular/core';
import { Item } from '../../../item';
import { ItemService } from '../../../item.service';
import { AuthService } from '../../../auth.service';

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent implements OnInit {

  item: Item;
  model:{bid?:number};
  constructor(
    private itemSerivce : ItemService,
    private authService : AuthService,
  ) {
    this.item = this.itemSerivce.getChosenItem();
    this.model={bid:this.item.currentPrice+this.item.bidIncrement};
  }
  isOwner():boolean{
    return this.authService.getLoggedInUsername()===this.item.user.username;
  }
  vege():boolean{
    return false;
  }
  makebid(){
    let bid:{item:Item, bidOffer:number}={item:{id:this.item.id}, bidOffer:this.model.bid};
    this.itemSerivce.makebid(bid);
  }
  ngOnInit() {
  }

}
