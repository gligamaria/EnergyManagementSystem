import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Person} from "../../common/person";
import {AuthService} from "../../services/auth.service";
import {PersonService} from "../../services/person.service";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit{

  users: Person[];
  person: Person;

  constructor(private personService: PersonService,
              private authService: AuthService,
              private route: ActivatedRoute) { }

  ngOnInit(){
    this.route.paramMap.subscribe(() =>{
      this.listQuestions();
    });
  }

  listQuestions(){
    this.handleList();
  }

  handleList(){
    this.personService.getUsersList().subscribe(
      data => {
        this.users = data;
      }
    )
  }
}
