export class Model {
  fromJson(json: Object) {
    console.log(Object.getOwnPropertyNames(this));
    for (let attribute of Object.getOwnPropertyNames(this)) {
      let propertyName = Reflect.getMetadata("json:property", this, attribute);
      if (propertyName) {
        //if has this metadata, change json attribute name
        this[attribute] = json[propertyName];
      }
      else {
        this[attribute] = json[attribute];
      }
    }
    return this;
  }

  toJson(){
    let result = {};
    for (let attribute in Object.getOwnPropertyNames(this)) {
      let propertyName = Reflect.getMetadata("json:property", this, attribute);
      if (propertyName) {
        //if has this metadata, change json attribute name
        result[propertyName] = this[attribute];
      }
      else {
        result[attribute] = this[attribute];
      }
    }
  }
}
