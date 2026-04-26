import { useEffect, useState } from "react";
import { Autocomplete, TextField } from "@mui/material";
import { useAuth } from "react-oidc-context";
import { fetchCountries, type Country } from "../../lib/countries-api";

type Props = {
  value?: Country | null;
  onChange?: (value: Country | null) => void;
  label?: string;
  size?: "small" | "medium";
  sx?: any;
  disabled?: boolean;
};

export default function CountrySelect({
  value,
  onChange,
  label = "Country",
  size = "small",
  sx,
  disabled,
}: Props) {
  const auth = useAuth();
  const [options, setOptions] = useState<Country[]>([]);
  const [loading, setLoading] = useState<boolean>(false);

  useEffect(() => {
    setLoading(true);
    fetchCountries("", 500, 0, "name,asc", auth)
      .then((res) => setOptions(res.content))
      .finally(() => setLoading(false));
  }, [auth]);

  return (
    <Autocomplete
      options={options}
      value={value ?? null}
      getOptionLabel={(opt) => opt.name}
      onChange={(_, v) => onChange && onChange(v)}
      loading={loading}
      size={size}
      sx={sx}
      disabled={disabled}
      renderInput={(params) => (
        <TextField {...params} label={label} size={size} />
      )}
    />
  );
}
