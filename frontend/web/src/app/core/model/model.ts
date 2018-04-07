export class Model {
  //NOTE: note that fromJson/toJson feature need to handle array properly before get to use
  static fromJson<T extends Model>(model: T, json: Object) {
    console.log(Object.getOwnPropertyNames(model));
    for (let attribute of Object.getOwnPropertyNames(model)) {
      let propertyName = Reflect.getMetadata("json:property", model, attribute);
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

  static toJson<T extends Model>(model: T) {
    let result = {};
    for (let attribute in Object.getOwnPropertyNames(model)) {
      let propertyName = Reflect.getMetadata("json:property", model, attribute);
      if (propertyName) {
        //if has this metadata, change json attribute name
        result[propertyName] = model[attribute];
      }
      else {
        result[attribute] = model[attribute];
      }
    }
  }
}
