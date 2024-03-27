import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { DeviceListComponent } from "./components/device-list/device-list.component";
import { LoginComponent } from './components/login/login.component';
import { BarChartComponent } from "./components/chart/bar-chart.component";
import { DatePickerComponent } from "./components/date-picker/date-picker.component";


import { RouterModule, Routes } from "@angular/router";
import { HttpClientModule } from "@angular/common/http";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";


import { MatNativeDateModule } from '@angular/material/core';
import { MatListModule } from '@angular/material/list';
import { MaterialExampleModule } from './material.module';
import { NewUserComponent } from "./components/new-user/new-user.component";
import { NgChartsModule } from 'ng2-charts';


import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';

import { MatRippleModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';



import {AdminGuard} from "./helpers/admin.guard";
import {UserDetailComponent} from "./components/user-detail/user-detail.component";
import {NewDeviceComponent} from "./components/new-device/new-device.component";
import {DeviceDetailComponent} from "./components/device-detail/device-detail.component";
import {DeviceDetailRestrictedComponent} from "./components/device-detail-restricted/device-detail-restricted.component";
import {MessageDisplayComponent} from "./components/message-display/message-display.component";
import {ChatListComponent} from "./components/chat-list/chat-list.component";
import {ClientChatComponent} from "./components/client-chat/client-chat.component";

const routes: Routes= [
  {path: 'login', component: LoginComponent},
  {path: 'users', component: UserListComponent, canActivate:[AdminGuard]},
  {path: 'devices', component: DeviceListComponent},
  {path: 'chat', component: ClientChatComponent},
  {path: 'chats', component: ChatListComponent},
  {path: 'users/new', component: NewUserComponent, canActivate:[AdminGuard]},
  {path: 'devices/new', component: NewDeviceComponent, canActivate: [AdminGuard]},
  {path: 'users/:id', component: UserDetailComponent,canActivate:[AdminGuard]},
  {path: 'devices/:id', component: DeviceDetailComponent,canActivate:[AdminGuard]},
  {path: 'devices/myDevice/:id', component: DeviceDetailRestrictedComponent},
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: '**', redirectTo: '/login', pathMatch: 'full'}
]

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    DeviceListComponent,
    LoginComponent,
    NewUserComponent,
    UserDetailComponent,
    NewDeviceComponent,
    DeviceDetailComponent,
    DeviceDetailRestrictedComponent,
    BarChartComponent,
    DatePickerComponent,
    MessageDisplayComponent,
    ChatListComponent,
    ClientChatComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    MatNativeDateModule,
    MatListModule,
    MaterialExampleModule,
    NgChartsModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatRippleModule,
    MatButtonModule,
    MatIconModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
