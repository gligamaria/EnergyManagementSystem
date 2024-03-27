
import {Component, ElementRef, ViewChild, Input} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FormControl} from '@angular/forms';
import {Person} from "../../common/person";
import {AuthService} from "../../services/auth.service";
import {PersonService} from "../../services/person.service";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {Observable, startWith} from "rxjs";
import {map} from "rxjs/operators";
import {MatChipInputEvent} from "@angular/material/chips";
import {MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {Router} from "@angular/router";
import {Device} from "../../common/device";
import {DeviceService} from "../../services/device.service";

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.css']
})
export class NewUserComponent {

  fileName = '';
  user: Person;
  date: Date;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  tagCtrl = new FormControl('');
  filteredTags: Observable<String[]>;
  allFreeDevices: Device[];
  someTags: Device[];
  tags: string[] = [];
  allFreeIds: String[];
  submitPressed: boolean = false;
  fileUrl: string | null = null;
  @Input() buttonText: string = "Add user";
  @Input() nameInputValue: string;
  @Input() emailInputValue: string;
  @Input() passwordInputValue: string;
  @Input() roleInputValue: string;
  @Input() updateUser: boolean;
  @Input() givenUser: Person;

  @ViewChild('deviceInput') deviceInput: ElementRef<HTMLInputElement>;

  constructor(private http: HttpClient,
              private authService: AuthService,
              private personService: PersonService,
              private deviceService: DeviceService,
              private router: Router) {

    this.deviceService.getFreeDevices().subscribe(
      data => {
        this.allFreeDevices = data;

      }
    );
    this.deviceService.getFreeIds().subscribe(
      data => {
        this.allFreeIds = data;
      }
    )
    this.filteredTags = this.tagCtrl.valueChanges.pipe(
      startWith(null),
      map((fruit: string | null) => (fruit ? this._filter(fruit) : this.allFreeIds)),
    );
  }

  onSubmit() {

    let role = 0;

    if(this.roleInputValue == "admin"){
      role = 1;
    }

    this.user = new Person("",this.nameInputValue,this.emailInputValue,role,this.passwordInputValue);

    if(this.updateUser){

      this.personService.updatePerson(this.user, this.givenUser.id).subscribe(
        (response) => {
          // Handle the successful response here, if needed
          console.log('User updated successfully', response);
          this.router.navigateByUrl(`/users`).then();
        },
        (error) => {
          // Handle errors here, if needed
          console.error('Error updating user', error);
        }
      );

      // this.user = new Person(-1,this.nameInputValue,this.emailInputValue,this.roleInputValue,this.passwordInputValue);
    }
    else{
      this.personService.addPerson(this.user).subscribe(
        (response) => {
          // Handle the successful response here, if needed
          console.log('User added successfully', response);
          this.router.navigateByUrl(`/users`).then();
        },
        (error) => {
          // Handle errors here, if needed
          console.error('Error adding user', error);
        }
      );
    }





    if(this.deviceInput){
      console.log(this.deviceInput);
    }
  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    let isValuePresent: boolean = false;

    if (value) {
      for (const tag of this.tags) {
        if (tag == value) {
          isValuePresent = true;
          break;
        }
      }
      if(!isValuePresent){
        this.tags.push(value);
      }
    }

    // Clear the input value
    event.chipInput!.clear();

    this.tagCtrl.setValue(null);
  }

  remove(fruit: string): void {
    const index = this.tags.indexOf(fruit);

    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.tags.push(event.option.viewValue);
    this.deviceInput.nativeElement.value = '';
    this.tagCtrl.setValue(null);
  }

  private _filter(value: string): String[] {
    const filterValue = value.toLowerCase();

    return this.allFreeIds.filter(fruit => fruit.toLowerCase().includes(filterValue));
  }

}
