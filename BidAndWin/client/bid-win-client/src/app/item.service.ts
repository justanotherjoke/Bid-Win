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
  listedItems: Item[];
  chosenItem: Item;
  bids:{
    id?:number;
    item?:Item;
    userId?:number;
    bidOffer?:number;
  }[];
  images:{
    id?:number;
    pic?:String;
    item?:Item;
  }[];
  constructor(
    private http: Http,
  ) { }
  getChosenItem():Item{
    return this.chosenItem;
  }
  setChosenItem(id:number){
    for(let i=0; i <this.items.length; i++ ){
      if(this.items[i].id===id){
        this.chosenItem=this.items[i];
        return;
      }
    }
  }
  uploadItem(item: Item):Promise<Item[]>{
    let img:{
      pic?:File,
      itemId?:number,
    };
    img={};
    img.pic=item.file;
    item.file=undefined;
    console.log(item);
    const response$: Observable<any> = this.http.post('/api/items/createitem', item);
    const responsePromise: Promise<any> = response$.toPromise();
    return responsePromise
      .then(res => res.json())
      .then(responseItem => {
        if(img.pic.size!==0){
          img.itemId=responseItem.id;
          this.uploadPicture(img);
        }
        return this.getAllItems();
      });
  }
  uploadPicture(img:{pic?:File, itemId?: number}):Promise<{
    id:number,
    pic:File,
    itemId:number,
  }>{
    const response$: Observable<any> = this.http.post('/api/image/uploadimage', img);
    const responsePromise: Promise<any> = response$.toPromise();
    return responsePromise
      .then(res => res.json())
      .then(responseImage => {
        this.images.push(responseImage);
        return responseImage;
      });
  }
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
  getItems():Item[]{
    return this.items;
  }
  getListedItems():Item[]{
    return this.listedItems;
  }
  listItems(categoryName:String){
    this.listedItems=[];
    if(categoryName===""){
      this.listedItems=this.items;
    }else{
      for(let i = 0; i < this.items.length; i++){
        if(this.items[i].category.name===categoryName){
          this.listedItems.push(this.items[i]);
        }
      }
    }
  }
  getAllCategories() :Promise<String[]>{
    const response$: Observable<any> = this.http.get('/api/category/all');
    const responsePromise: Promise<any> = response$.toPromise();
    return responsePromise
      .then(res => res.json())
      .then(responseCategories => {
        this.categories = responseCategories;
        console.log(this.categories);
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
  fuseItemsBidsImages(){
    for(let i = 0; i<this.items.length; i++){
      let bidkesz=false;
      let pickesz=false;
      for(let j = 0; j<this.bids.length || j<this.images.length; j++){
        if(this.items[i].bestBidderId===-1){
          bidkesz=true;
        }
        if(!bidkesz &&j<this.bids.length){
          if(this.bids[j].item.id===this.items[i].bestBidderId){
            this.items[i].currentPrice=this.bids[j].bidOffer;
            bidkesz=true;
          }
        }
        if(!pickesz && j<this.images.length){
          if(this.images[j].item.id===this.items[i].id){
            this.items[i].picture=this.images[j].pic;
            pickesz=true;
          }
        }
        if((bidkesz || j>this.bids.length)&& (pickesz || j>this.images.length)){
          break;
        }
      }
      if(isUndefined(this.items[i].currentPrice) || this.items[i].currentPrice===-1){
        this.items[i].currentPrice=this.items[i].startPrice;
      }
      if(isUndefined(this.items[i].picture)){
        console.log(this.items[i].picture);
        this.items[i].picture="/9j/4AAQSkZJRgABAQIAHAAcAAD/2wBDAAUDBAQEAwUEBAQFBQUGBwwIBwcHBw8LCwkMEQ8SEhEPERETFhwXExQaFRERGCEYGh0dHx8fExciJCIeJBweHx7/2wBDAQUFBQcGBw4ICA4eFBEUHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh7/wAARCAFeAV4DASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD7LooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAxvFfiG38PW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r0SKOOKJYokVI0AVVUYAA6ACnUUAFUfEEck2gahDEheR7WVVUDJJKEACr1FAHi1lbajHZwRvo+rBljVSPsMnUD6VL5N//wBAfVv/AABk/wDia9kooA5/4dW9xa+C9OguoJIJlRt0cilWXLseQenBroKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAOB+LH/ACENB/35/wD0Ba5ius+KdrdzXGjTW1ndXKRPN5nkRNIVyoxkAVynk3//AEB9W/8AAGT/AOJoALT/AJD+i/8AYRh/ma9kryHTbPUZtf0gjStSRY76OR3ktHRVUZySSMV69QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAf//Z";
      }
    }
  }
}
