import {Component, Input} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {PersonService} from "../../services/person.service";
import {DeviceService} from "../../services/device.service";
import {Device} from "../../common/device";
import {Person} from "../../common/person";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent {

  devices: String[];
  freeDevices: String[];
  user: Person | null;
  safeDelete: String;
  edit: boolean = false;
  tagList: String[];
  role: string = 'admin';
  @Input() newDeviceInputValue: string;

  constructor(private deviceService: DeviceService,
              private route: ActivatedRoute,
              private personService: PersonService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.handleUserDetails()
    })
  }

  deleteUser(){

    this.deviceService.removePerson(this.user!.id).subscribe();

    this.personService.deletePerson(this.user!.id).subscribe({
      next: () => {
        this.router.navigateByUrl(`users`).then();
      }
    });
  }

  private handleUserDetails() {

    const  userId: string = this.route.snapshot.paramMap.get('id')!;
    this.personService.getUser(userId).subscribe(
      data => {
        this.user = data;
      }
    )

    this.deviceService.getIdsByUser(userId).subscribe(
      data => {
        this.devices = data;
      }
    )

    this.deviceService.getFreeIds().subscribe(
      data => {
        this.freeDevices = data;
      }
    )

  }

  editUser() {
    this.edit = true;
    if(this.user!.role == 0){
      this.role = 'client';
    }
  }

  addDevice() {
    console.log(this.newDeviceInputValue);
    this.deviceService.addUserToDevice(this.newDeviceInputValue, this.user!.id).subscribe({
      next: () => {
        location.reload();
      }
    });
  }
}
