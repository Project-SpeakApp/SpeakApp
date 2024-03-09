export class CalculateNumberOfRows {
  static calculateNumberOfRows(input: string) : number {
    let newlines = 0;
    let otherCharacters = 0;
    for (const char of input) {
      if (char === '\n') {
        newlines++;
      } else {
        otherCharacters++;
      }
    }
    return otherCharacters/65 + newlines + 1;
  }
}
