import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import {Category} from "./category"

@Injectable()
export class ItemService {
  categories: Category[];
  constructor(
    private http: Http,
  ) { }
  getAllCategories() :Promise<String[]>{
    const response$: Observable<any> = this.http.get('/api/category/all');
    const responsePromise: Promise<any> = response$.toPromise();
    return responsePromise
      .then(res => res.json())
      .then(responseCategories => {
        this.categories = responseCategories;
        return responseCategories;
      });
  }
  getCategoriesName() : String[]{
    let names :String[]=[];
    for (let i = 0; i < this.categories.length; i++ ){
      names[i]=(this.categories[i].name);
    }
    return names;
  }
  addCategory(category: Category) :Promise<Category[]>{
    const response$: Observable<any> = this.http.post('/api/category/createcategory', category);
    const responsePromise: Promise<any> = response$.toPromise();
    return responsePromise
      .then(res => res.json())
      .then(responseCategories => {
        this.categories = responseCategories;
        return responseCategories;
      });
  }
}
