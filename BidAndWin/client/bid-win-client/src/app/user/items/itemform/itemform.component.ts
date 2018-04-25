import { Component, OnInit } from '@angular/core';
import { Category } from '../../../category';
import { ItemService } from '../../../item.service';
@Component({
  selector: 'app-itemform',
  templateUrl: './itemform.component.html',
  styleUrls: ['./itemform.component.css']
})
export class ItemformComponent implements OnInit {

  model: Category;
  categories: Category[];
  constructor(
    itemService: ItemService,
  ) {
    itemService.getAllCategories();
    this.categories=itemService.getCategories();
    console.log(this.categories);
    this.model={
      "name":'',
    };
  }

  ngOnInit() {
  }

}
