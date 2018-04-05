import 'reflect-metadata'

export function JsonProperty(name: string) {
  return Reflect.metadata("json:property", name);
}
