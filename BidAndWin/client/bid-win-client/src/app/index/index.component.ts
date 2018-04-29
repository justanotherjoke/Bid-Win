import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { ItemService } from '../item.service';
@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {
  
  constructor(
    private authService: AuthService,
    private itemService: ItemService,
  ) { }

  ngOnInit() {
    
  }

}
