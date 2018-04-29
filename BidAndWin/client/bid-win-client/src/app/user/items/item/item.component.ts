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
  model: Item;
  constructor(
    private itemSerivce : ItemService,
    private authService : AuthService,
  ) {
    this.item = this.itemSerivce.getChosenItem();
    this.model = this.item;
  }
  isOwner():boolean{
    return this.authService.getLoggedInUsername()===this.model.user.username;
  }
  vege():boolean{
    return false;
  }
  ngOnInit() {
  }

}
