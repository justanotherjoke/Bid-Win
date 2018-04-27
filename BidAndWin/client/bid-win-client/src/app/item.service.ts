import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import {Category} from "./category";
import {Item} from "./item";

@Injectable()
export class ItemService {
  categories: Category[];
  items: Item[];
  bids:{
    id?:number;
    itemId?:number;
    userId?:number;
    bidOffer?:number;
  }[];
  images:{
    id?:number;
    pic?:String;
    itemId?:number;
  }[];
  constructor(
    private http: Http,
  ) { }
  getAllItems() :Promise<Item[]>{
    const response$: Observable<any> = this.http.get('/api/items/all');
    const responsePromise: Promise<any> = response$.toPromise();
    return responsePromise
      .then(res => res.json())
      .then(responseItems => {
        this.items = responseItems;
        return responseItems;
      });
  }
  getAllBids():Promise<{
    id?:number;
    itemid?:number;
    userid?:number;
    bidOffer?:number;
  }[]>
  {
    const response$: Observable<any> = this.http.get('/api/bids/all');
    const responsePromise: Promise<any> = response$.toPromise();
    return responsePromise
      .then(res => res.json())
      .then(responseBids => {
        this.bids = responseBids;
        return responseBids;
      });
  }
  fuseItemsBidsImages(){
    for(let i = 0; i<this.items.length; i++){
      for(let j = 0; j<this.bids.length || j<this.images.length; j++){
          let bidkesz=false;
          let pickesz=false;
        if(this.bids[j].itemId===this.items[i].id){
          this.items[i].currentPrice=this.bids[j].bidOffer;
          bidkesz=true;
        }
        if(this.images[j].itemId===this.items[i].id){
          this.items[i].picture===this.images[j].pic;
          pickesz=true;
        }
        if(bidkesz && pickesz){
          break;
        }
      }
    }
  }
  getItems():Item[]{
    return this.items;
  }
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
