Prism.languages.structuredText = {
    'comment': [
        /\(\*[\s\S]+?\*\)/,
        /\{[\s\S]+?\}/,
        /\/\/.*/
    ],
    'string': {
        pattern: /(?:'(?:''|[^'\r\n])*'(?!')|#[&$%]?[a-f\d]+)+|\^[a-z]/i,
        greedy: true
    },
    'keyword': [
        {
            // base
            patern: /(^|[^&])\b(?:program|end_program)\b/i,
        },
        {
            //date types
            pattern: /(^|[^&])\b(?:bool|byte|word|int|real|date|time|string|dinit|sinit|udint|unit|usint)\b/i,
            lookbehind: true
        },
        {
            //conditions
            pattern: /(^|[^&])\b(?:if|else|then|case|elseif|end_if|end_case|of)\b/i,
            lookbehind: true
        },
        {
            //cycles
            pattern: /(^|[^&])\b(?:for|while|repeat|return|exit|to|end_for|end_while|end_repeate|by|do)\b/i,
            lookbehind: true
        },
    ],
    'number': [
        // Hexadecimal, octal and binary
        /(?:[&%]\d+|\$[a-f\d]+)/i,
        // Decimal
        /\b\d+(?:\.\d+)?(?:e[+-]?\d+)?/i
    ],
    'operator': [
        /\.\.|\*\*|:=|<[<>]?|>[>=]?|[+\-*\/]=?|[@^=]/i,
        {
            pattern: /(^|[^&])\b(?:and|or|xor|mod|not|&)\b/,
            lookbehind: true
        }
    ],
    'punctuation': /\(\.|\.\)|[()\[\]:;,.]/
};
