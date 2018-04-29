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
  categoryNames: String[];
  constructor(
    private itemService: ItemService,
  ) {
    itemService.getAllCategories();
    this.categoryNames=itemService.getCategoriesName();
    console.log(this.categoryNames);
    this.model={
      "name":'',
    };
  }

  ngOnInit() {
  }
  onSubmit(){
    this.itemService.listItems(this.model.name);
  }
}
