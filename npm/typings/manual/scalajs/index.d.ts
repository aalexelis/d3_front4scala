declare namespace scalajs {
    const Validator: {
        isValidEmail: (s: string) => boolean;
        isValidPhone: (s: string) => boolean;
        isValidDate: (s: string) => boolean;
        maxLength: (s: string, l: number) => boolean;
        minLength: (s: string, l: number) => boolean;
    }
    const Catalog: {
        Countries: Array<{
            id: string;
            label: string;
        }>
    }
}