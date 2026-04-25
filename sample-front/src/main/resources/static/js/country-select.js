class CountrySelect extends HTMLElement {
  constructor() {
    super();
    this._shadow = this.attachShadow({ mode: "open" });
    this._select = document.createElement("select");
    this._selectPart = document.createElement("div");
  }

  connectedCallback() {
    const name = this.getAttribute("name") || "countryId";
    const apiUrl =
      this.getAttribute("api-url") || "/api/v1/countries?size=1000";
    const placeholder = this.getAttribute("placeholder") || "Select country";
    const value = this.getAttribute("value");

    this._select.name = name;
    this._select.id =
      this.getAttribute("id") ||
      `country-select-${Math.random().toString(36).slice(2, 8)}`;

    const style = document.createElement("style");
    style.textContent = `
      :host { display: inline-block; }
      select { min-width: 220px; padding:6px 8px; }
    `;

    this._shadow.appendChild(style);

    // build wrapper
    const wrapper = document.createElement("div");
    wrapper.setAttribute("class", "country-select-wrapper");

    const selectEl = this._select;

    const placeholderOption = document.createElement("option");
    placeholderOption.value = "";
    placeholderOption.textContent = placeholder;
    selectEl.appendChild(placeholderOption);

    wrapper.appendChild(selectEl);

    this._shadow.appendChild(wrapper);

    // Fetch countries and populate (expects PageResponse with 'content' or an array)
    fetch(apiUrl)
      .then((r) => {
        if (!r.ok) throw new Error("network");
        return r.json();
      })
      .then((json) => {
        const items =
          json && json.content ? json.content : Array.isArray(json) ? json : [];
        items.forEach((c) => {
          const opt = document.createElement("option");
          opt.value =
            c.id !== undefined && c.id !== null
              ? c.id
              : c.code !== undefined
                ? c.code
                : "";
          opt.textContent = c.name || c.label || String(opt.value);
          selectEl.appendChild(opt);
        });
        if (value) selectEl.value = value;
      })
      .catch((err) => {
        console.warn("country-select: error fetching countries", err);
      });

    selectEl.addEventListener("change", (e) => {
      this.dispatchEvent(
        new CustomEvent("change", { detail: { value: selectEl.value } }),
      );
    });
  }

  // reflect value attribute
  static get observedAttributes() {
    return ["value"];
  }
  attributeChangedCallback(name, oldV, newV) {
    if (name === "value" && this._select) this._select.value = newV || "";
  }

  // convenience getter
  get value() {
    return this._select ? this._select.value : null;
  }
  set value(v) {
    if (this._select) this._select.value = v;
  }
}

customElements.define("country-select", CountrySelect);
