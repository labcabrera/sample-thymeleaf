import { useEffect, useState } from "react";
import { Autocomplete, TextField } from "@mui/material";
import { useAuth } from "react-oidc-context";
import { fetchProvinces, type Province } from "../../lib/provinces-api";

type Props = {
  value?: Province | null;
  onChange?: (value: Province | null) => void;
  label?: string;
  size?: "small" | "medium";
  disabled?: boolean;
};

export default function ProvinceSelect({
  value,
  onChange,
  label = "Province",
  size = "small",
  disabled,
}: Props) {
  const auth = useAuth();
  const [options, setOptions] = useState<Province[]>([]);

  useEffect(() => {
    fetchProvinces("", 500, 0, "name,asc", auth).then((res) =>
      setOptions(res.content),
    );
  }, [auth]);

  return (
    <Autocomplete
      options={[{ id: "", name: "" } as Province, ...options]}
      value={value ?? null}
      getOptionLabel={(opt) => opt?.name ?? ""}
      onChange={(_, v) => onChange && onChange(v && v.id !== "" ? v : null)}
      size={size}
      disabled={disabled}
      fullWidth
      renderInput={(params) => (
        <TextField {...params} label={label} size={size} />
      )}
    />
  );
}
