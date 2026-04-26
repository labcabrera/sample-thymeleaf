import { IconButton, InputAdornment, TextField } from "@mui/material";
import ClearIcon from "@mui/icons-material/Clear";

type Props = {
  label: string;
  value: string | null;
  onChange: (value: string | null) => void;
};

export default function ClearableTextField({ label, value, onChange }: Props) {
  return (
    <TextField
      label={label}
      value={value}
      size="small"
      onChange={(e) => onChange(e.target.value)}
      fullWidth
      slotProps={{
        input: {
          endAdornment: value ? (
            <InputAdornment position="end">
              <IconButton edge="end" onClick={() => onChange(null)}>
                <ClearIcon />
              </IconButton>
            </InputAdornment>
          ) : null,
        },
      }}
    />
  );
}
