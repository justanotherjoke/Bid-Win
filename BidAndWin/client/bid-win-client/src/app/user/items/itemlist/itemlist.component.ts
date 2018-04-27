import { Component, OnInit } from '@angular/core';
import { ItemService } from '../../../item.service';
import { Item } from '../../../item';

@Component({
  selector: 'app-itemlist',
  templateUrl: './itemlist.component.html',
  styleUrls: ['./itemlist.component.css']
})
export class ItemlistComponent implements OnInit {
  items: Item[];

  constructor(
    private itemService: ItemService,
  ) {
    this.items = itemService.getItems();
  }

  ngOnInit() {
  }
  getImage(id:number){
    
  }
}
