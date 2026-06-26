type Language = {
    name: string;
    paradigm: string;
};

const language: Language = {
    name: "TypeScript",
    paradigm: "multi-paradigm",
};

function describeLanguage(value: Language): string {
    return `${value.name} is a ${value.paradigm} programming language.`;
}

console.log(describeLanguage(language));
