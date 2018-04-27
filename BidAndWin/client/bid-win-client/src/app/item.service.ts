import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import {Category} from "./category";
import {Item} from "./item";
import { isUndefined } from 'util';
import { and } from '@angular/router/src/utils/collection';

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
  getAllImages() :Promise<{
    id?:number;
    pic?:String;
    itemId?:number;
  }[]>{
    const response$: Observable<any> = this.http.get('/api/image/all');
    const responsePromise: Promise<any> = response$.toPromise();
    return responsePromise
      .then(res => res.json())
      .then(responseImages => {
        this.images = responseImages;
        console.log(this.images);
        return responseImages;
      });
  }
  getAllItems() :Promise<Item[]>{
    const response$: Observable<any> = this.http.get('/api/items/all');
    const responsePromise: Promise<any> = response$.toPromise();
    return responsePromise
      .then(res => res.json())
      .then(responseItems => {
        this.items = responseItems;
        console.log(this.items);
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
        console.log(this.bids);
        return responseBids;
      });
  }
  fuseItemsBidsImages(){
    for(let i = 0; i<this.items.length; i++){
      let bidkesz=false;
      let pickesz=false;
      for(let j = 0; j<this.bids.length || j<this.images.length; j++){
        if(!bidkesz &&j<this.bids.length){
          if(this.bids[j].itemId===this.items[i].id){
            this.items[i].currentPrice=this.bids[j].bidOffer;
            bidkesz=true;
          }
        }
        if(!pickesz && j<this.images.length){
          if(this.images[j].itemId===this.items[i].id){
            this.items[i].picture=this.images[j].pic;
            pickesz=true;
          }
        }
        if((bidkesz || j>this.bids.length)&& (pickesz || j>this.images.length)){
          break;
        }
      }
      console.log(this.items[i].picture);
      if(isUndefined(this.items[i].picture)){
        console.log(this.items[i].picture);
        this.items[i].picture='';
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
