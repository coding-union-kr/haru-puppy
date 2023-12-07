import "styled-components";

declare module "styled-components" {
    export interface DefaultTheme {
        colors: {
            main: string,
            light: string,
            secondary: string,
            background: string,
            alert: string,
            black90: string,
            black80: string,
            black70: string, //grey-text-color
            black60: string, //border-color
            black50: string, //border-color
        },

        typo: {
            regular: number,
            medium: number,
            semibold: number,
        }
    }
}