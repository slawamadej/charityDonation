document.addEventListener("DOMContentLoaded", function() {

  class FormSelect {
    constructor($el) {
      this.$el = $el;
      this.options = [...$el.children];
      this.init();
    }

    init() {
      this.createElements();
      this.addEvents();
      this.$el.parentElement.removeChild(this.$el);
    }

    createElements() {
      // Input for value
      this.valueInput = document.createElement("input");
      this.valueInput.type = "text";
      this.valueInput.name = this.$el.name;

      // Dropdown container
      this.dropdown = document.createElement("div");
      this.dropdown.classList.add("dropdown");

      // List container
      this.ul = document.createElement("ul");

      // All list options
      this.options.forEach((el, i) => {
        const li = document.createElement("li");
        li.dataset.value = el.value;
        li.innerText = el.innerText;

        if (i === 0) {
          // First clickable option
          this.current = document.createElement("div");
          this.current.innerText = el.innerText;
          this.dropdown.appendChild(this.current);
          this.valueInput.value = el.value;
          li.classList.add("selected");
        }

        this.ul.appendChild(li);
      });

      this.dropdown.appendChild(this.ul);
      this.dropdown.appendChild(this.valueInput);
      this.$el.parentElement.appendChild(this.dropdown);
    }

    addEvents() {
      this.dropdown.addEventListener("click", e => {
        const target = e.target;
        this.dropdown.classList.toggle("selecting");

        // Save new value only when clicked on li
        if (target.tagName === "LI") {
          this.valueInput.value = target.dataset.value;
          this.current.innerText = target.innerText;
        }
      });
    }
  }
  document.querySelectorAll(".form-group--dropdown select").forEach(el => {
    new FormSelect(el);
  });

  /**
   * Hide elements when clicked on document
   */
  document.addEventListener("click", function(e) {
    const target = e.target;
    const tagName = target.tagName;

    if (target.classList.contains("dropdown")) return false;

    if (tagName === "LI" && target.parentElement.parentElement.classList.contains("dropdown")) {
      return false;
    }

    if (tagName === "DIV" && target.parentElement.classList.contains("dropdown")) {
      return false;
    }

    document.querySelectorAll(".form-group--dropdown .dropdown").forEach(el => {
      el.classList.remove("selecting");
    });
  });

  /**
   * Switching between form steps
   */
  class FormSteps {
    constructor(form) {
      this.$form = form;
      this.$next = form.querySelectorAll(".next-step");
      this.$prev = form.querySelectorAll(".prev-step");
      this.$step = form.querySelector(".form--steps-counter span");
      this.currentStep = 1;

      this.$stepInstructions = form.querySelectorAll(".form--steps-instructions p");
      const $stepForms = form.querySelectorAll("form > div");
      this.slides = [...this.$stepInstructions, ...$stepForms];

      this.init();
    }

    /**
     * Init all methods
     */
    init() {
      this.events();
      this.updateForm();
    }

    /**
     * All events that are happening in form
     */
    events() {
      // Next step
      this.$next.forEach(btn => {
        btn.addEventListener("click", e => {
          e.preventDefault();
          if(this.validationForm()){
            this.currentStep++;
            this.updateForm();
          }
        });
      });

      // Previous step
      this.$prev.forEach(btn => {
        btn.addEventListener("click", e => {
          e.preventDefault();
          if(this.validationForm()){
            this.currentStep--;
            this.updateForm();
          }
        });
      });

      // Form submit
      this.$form.querySelector("form").addEventListener("submit", e => this.submit(e));
    }

    /**
     * Update form front-end
     * Show next or previous section etc.
     */
    updateForm() {
      this.$step.innerText = this.currentStep;

      this.slides.forEach(slide => {
        slide.classList.remove("active");

        if (slide.dataset.step == this.currentStep) {
          slide.classList.add("active");
        }
      });

      this.$stepInstructions[0].parentElement.parentElement.hidden = this.currentStep >= 5;
      this.$step.parentElement.hidden = this.currentStep >= 5;

    }

    validationForm() {
      this.$step.innerText = this.currentStep;
      if(this.currentStep == 1 && document.querySelectorAll('input[name="categories"]:checked').length == 0){
        return false;
      }
      if(this.currentStep == 2 && (document.querySelector("#quantity").value == null || document.querySelector("#quantity").value < 1)){
        return false;
      }
      if(this.currentStep == 3 && document.querySelector('input[name="institution"]:checked').length == 0){
        return false;
      }
      if(this.currentStep == 4 &&(document.querySelector("#street").value == ""
      || document.querySelector("#city").value == ""
      ||  document.querySelector("#zipCode").value == ""
      || document.querySelector('input[name="phone"]').value == ""
      /*|| document.querySelector("#pickUpDate").value == ""
      || document.querySelector("#pickUpTime").value == ""
      || document.querySelector("#pickUpComment").value == ""*/)){
        return false;
      }

      return true;
    }

  }

  document.querySelector("#donationSummaryButton").addEventListener("click", function(){
    let quantity = document.querySelector("#quantity").value;
    let quantitySummary;
    if(quantity == 1){
      quantitySummary = quantity + " worek";
    } else if (quantity%10 > 1 && quantity%10 <5){
      quantitySummary = quantity + " worki";
    } else {
      quantitySummary = quantity + " worków";
    }

    let categoriesSummary;
    let counter = 0;
    document.querySelectorAll('input[name="categories"]:checked').forEach(el => {
      if(counter > 0){
        categoriesSummary = categoriesSummary + ", " + el.parentElement.childNodes[5].textContent;
      } else {
        categoriesSummary = el.parentElement.childNodes[5].textContent;
      }
      counter ++;
    });

    let donationSummary = quantitySummary + " " + categoriesSummary.replaceAll("ubrania", "ubrań").replaceAll("zabawki", "zabawek")
        .replaceAll("książki", "książek").replaceAll("inne", "innych rzeczy");
    let fundation = document.querySelector('input[name="institution"]:checked').parentElement.childNodes[5].childNodes[1].firstChild.textContent;
    let fundationSummary = "Dla Fundacji" + fundation.replace('Fundacja','');

    let street = document.querySelector("#street").value;
    let city = document.querySelector("#city").value;
    let zipCode = document.querySelector("#zipCode").value;
    let phone = document.querySelector('input[name="phone"]').value;
    let date = document.querySelector("#pickUpDate").value;
    let time = document.querySelector("#pickUpTime").value;
    let comment = document.querySelector("#pickUpComment").value;

    document.querySelector("#donationSummary").innerHTML = donationSummary;
    document.querySelector("#fundationSummary").innerHTML = fundationSummary;
    document.querySelector("#streetSummary").innerHTML = street;
    document.querySelector("#citySummary").innerHTML = city;
    document.querySelector("#zipCodeSummary").innerHTML = zipCode;
    document.querySelector("#phoneSummary").innerHTML = "tel. " + phone;
    document.querySelector("#dateSummary").innerHTML = date;
    document.querySelector("#timeSummary").innerHTML = time;
    document.querySelector("#commentSummary").innerHTML = comment;

  })

  const form = document.querySelector(".form--steps");
  if (form !== null) {
    new FormSteps(form);
  }
});
