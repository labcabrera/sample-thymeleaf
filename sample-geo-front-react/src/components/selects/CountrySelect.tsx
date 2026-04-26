import { useEffect, useState } from "react";
import { Autocomplete, TextField } from "@mui/material";
import { useAuth } from "react-oidc-context";
import { fetchCountries, type Country } from "../../lib/countries-api";

type Props = {
  value?: Country | null;
  label?: string;
  disabled?: boolean;
  allOption?: boolean;
  onChange?: (value: Country | null) => void;
};

export default function CountrySelect({
  value,
  label = "Country",
  disabled,
  allOption = false,
  onChange,
}: Props) {
  const auth = useAuth();
  const [options, setOptions] = useState<Country[]>([]);

  useEffect(() => {
    fetchCountries("", 500, 0, "name,asc", auth).then((res) =>
      setOptions(
        allOption
          ? [{ id: "", name: "ALL" } as Country, ...res.content]
          : res.content,
      ),
    );
  }, [auth, allOption]);

  return (
    <Autocomplete
      options={options}
      value={value ?? null}
      getOptionLabel={(opt) => opt?.name ?? ""}
      onChange={(_, v) => onChange && onChange(v && v.id !== "" ? v : null)}
      disabled={disabled}
      fullWidth
      renderInput={(params) => (
        <TextField {...params} label={label} size="small" />
      )}
    />
  );
}
