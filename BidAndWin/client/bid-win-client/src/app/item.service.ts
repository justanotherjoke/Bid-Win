import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class ItemService {
  categories: String[];
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
  getCategories() : String[]{
    this.getAllCategories();
    setTimeout(() => {
      
    }, 300);
    console.log(this.categories);
    return this.categories;
  }
  addCategory(category: String) :Promise<String[]>{
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
