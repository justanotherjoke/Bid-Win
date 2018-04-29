import { ItemService } from './item.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { IndexComponent } from './index/index.component';
import { RegistrationComponent } from './registration/registration.component';
import { HeaderComponent } from './header/header.component';
import { AuthService } from './auth.service';
import { RegisterService } from './register.service';
import { SettingsComponent } from './user/settings/settings.component';
import { ItemlistComponent } from './user/items/itemlist/itemlist.component';
import { ItemformComponent } from './user/items/itemform/itemform.component';
import { ItemuploadComponent } from './user/items/itemupload/itemupload.component';
import { ItemsComponent } from './user/items/items.component';
import { ItemComponent } from './user/items/item/item.component';
const appRoutes : Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'index', component: IndexComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'user/settings', component: SettingsComponent},
  {path: 'user/items', component: ItemsComponent},
  {path: 'user/items/item', component: ItemComponent},
  {path: 'user/items/itemupload', component: ItemuploadComponent},
  {path: '**', redirectTo: 'index'},
];
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    IndexComponent,
    RegistrationComponent,
    HeaderComponent,
    SettingsComponent,
    ItemlistComponent,
    ItemformComponent,
    ItemuploadComponent,
    ItemsComponent,
    ItemComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(
      appRoutes, {enableTracing: true}
    )
  ],
  providers: [AuthService, RegisterService, ItemService],//ide kell írni majd a Service-eket.
  bootstrap: [AppComponent]
})
export class AppModule { }
