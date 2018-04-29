import { Component, OnInit } from '@angular/core';
import { ItemService } from '../../../item.service';
import { Item } from '../../../item';
import { AuthService } from '../../../auth.service';

@Component({
  selector: 'app-itemlist',
  templateUrl: './itemlist.component.html',
  styleUrls: ['./itemlist.component.css']
})
export class ItemlistComponent implements OnInit {
  items: Item[];
  constructor(
    private itemService: ItemService,
    private authService: AuthService,
  ) {
    if(this.authService.isLoggedIn()){
      this.itemService.fuseItemsBidsImages();
    }
    this.itemService.listItems("");
    this.items = this.itemService.getListedItems();
  }

  ngOnInit() {
  }
  setChosenItem(itemid:number){
    this.itemService.setChosenItem(itemid);
  }
  vege(id:number):boolean{
    return false;
  }
}
