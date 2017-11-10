---
@title[Frontpage]
## Scalaバックエンドシステムのフロント選択: ScalaJS or Typescript？

#### Andreas Alexelis
#### Bizreach Co. Ltd., 2017-11-10
---
@title[About the presenter]
## About the presenter

- Name: Andreas Alexelis
- Nationality: Greece
- Age: 45 :scream:
- First computer was a [TI-994A](http://oldcomputers.net/ti994a.html) with Basic & Assembly
- Bored with Pascal/Fortran at University
- Produced many segfaults in C,C++ during Master Thesis
- Suffered enterprise Java at a big Japanese maker company
- Had several nervous breakdowns from Javascript when free-lancing
- Happily writing Scala/... at Stanby, Bizreach currently.
---
@title[Introduction]
## Introduction
Let's get to know each other!
- What kind of team do you work in (BE engineers, FE engineers, designers)
- What backend technologies do you use?
- What frontend technologies do you use?
- Do you have any problems with the level of integration?
---
@title[Problems]
## The problem
How do we know we have a problem?
- Data structures have to be declared and maintained separately
- There is similar code back and front
- things break silently _(js :scream:)_ when sync is lost
---
@title[Problems: Duplicate data models]
### Data structures have to be declared and maintained separately

+++?code=shared/src/main/scala/shared/Catalog.scala&lang=Scala&title=CatalogBackend
+++?code=/npm/ts/examples/typescript/Countries.ts&lang=Typescript&title=CatalogFrontend

##### What will happen if I add a country only in frontend? _Note to Self: Make common!_
---
@title[Problems: Duplicate code]
### There is similar code back and front

+++?code=jvm/app/utils/Validator.scala&lang=Scala
+++?code=/npm/ts/examples/typescript/FormValidator.ts&lang=Typescript

##### They look so alike!　_Note to Self: DRY!_
---
@title[Problems: Silent errors]
### Things break silently when sync is lost

---
@title[More problems]
## More problems
- Frontend version management is tedious
- Skillset of dev-team
- Security
---
@title[More problems: Frontend version management]
## Frontend version management is tedious

![npm outdated](pitchme_assets/maintenance.png)
---
@title[More problems: Team skillset]
## Skillset of dev-team

- Can frontend developers understand and __maintain__ a codebase that uses technologies like ScalaJs (or even Twirl!)?
- Can backend developers understand and __maintain__ a codebase that uses technologies like Redux, Saga etc?
- How long does it take to add a button to a screen?
  A property to a JSON model?
  A new screen?
---
@title[More problems: Security]
## Security

- One always has to be careful
- With SPA, you have to be __very__ careful
---
@title[Basics: Team]
## Team

### Members
- Back-end developers
- Front-end developers
- Designers

### Issues to think about:
- What language do they speak?
- What libraries do they use?
---
@title[Basics: Architecture]
## Architecture
- The MVC paradigm
![mvc](https://www.codeproject.com/KB/aspnet/344292/mvc.PNG)
- The MVVM paradigm
![mvvm](https://cdn-images-1.medium.com/max/1600/1*BpxMFh7DdX0_hqX6ABkDgw.png)
---
@title[Basics: Architecture]
## Architecture
- __The role of Javascript__
- Modern system design paradigm
  - Front-end View on the device -> UI/UX
  - Front-end Controller or ViewModel -> Local state, change management & back-end interface
  - Back-end Controller -> front-end interface
  - Back-end Model -> Business Logic
---
@title[Evolution of Web Apps]
## Evolution of Web applications ##
- Plain HTML -> Form example with back-end validation
  - None of the problems but really lame
- HTML + js file -> Form example
  - Breaks immediately when not maintained properly
- Server rendered HTML + embedded js
  - Solves one part of maintenance problem, but js is kept embedded within host language, which has it's own problems
- Server rendered HTML + js-producing display
  - Difficult to read. Limiting regarding the js it produces.
  ##### TO BE CONTINUED #####
---
@Title[Typescript]
## Typescript
- [www.typescriptlang.org](https://www.typescriptlang.org/)
- (Relatively) type-safe, modular, lightweight front-end language that compiles to javascript
- It provides compile time checks for javascript
- Silent failures are minimized. Typing allows some level of interface compatibility check
- Cannot share code with backend
---
@Title[ScalaJs]
## ScalaJS
- [www.scala-js.org](https://www.scala-js.org/)
- javascript derived from Scala code, produced from the Scala compiler.
- Allow you to write front-end code within the compiling system of the backend code.
- Code sharing between backend and front-end
- It is difficult for front-end developers. Code sharing level decreases when trying to share code with native js libraries.
---
@title[Evolution of Web Apps cont.]
## Evolution of Web Apps cont. ##
- API backend + Typescript Frontend
  - Solves the silent failure mode, but does not allow code sharing.
- Integrated Scala backend + ScalaJS frontend
  - Solves the code sharing problem, but it is hard for the front-end developers.
- Integrated Scala backend + ScalaJs + Typescript frontend
  - Seems promising!
---
@title[Code Samples]
## Various Code Samples ##
---
@title[QA]
## THANKS FOR LISTENING ##
## It's Q&A time! ##
